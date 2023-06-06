build:
	podman build --label "app=gifttrack" -f Dockerfile -t quay.io/ralvares/santa:gifttrack .
	podman build --label "app=deliveryelf" -f Dockerfile -t quay.io/ralvares/santa:deliveryelf .
	podman build --label "app=festiveroute" -f Dockerfile -t quay.io/ralvares/santa:festiveroute .
	podman build --label "app=giftguardian" -f Dockerfile -t quay.io/ralvares/santa:giftguardian .
	podman build --label "app=joyrider" -f Dockerfile -t quay.io/ralvares/santa:joyrider .
	podman build --label "app=presentpro" -f Dockerfile -t quay.io/ralvares/santa:presentpro .
	podman build --label "app=santadispatch" -f Dockerfile -t quay.io/ralvares/santa:santadispatch .
	podman build --label "app=wrapmaster" -f Dockerfile -t quay.io/ralvares/santa:wrapmaster .


	
push:
	podman push quay.io/ralvares/santa:gifttrack
	podman push quay.io/ralvares/santa:deliveryelf
	podman push quay.io/ralvares/santa:festiveroute
	podman push quay.io/ralvares/santa:giftguardian
	podman push quay.io/ralvares/santa:joyrider
	podman push quay.io/ralvares/santa:presentpro
	podman push quay.io/ralvares/santa:santadispatch
	podman push quay.io/ralvares/santa:wrapmaster
