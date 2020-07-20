var socket = null;
	
$(document).ready(function (){
	if(loginID){
		send_alarms(loginID);
	}
});

function send_alarms(id) {
    var wsUri = "ws://localhost:8080/echo";
    console.log(id);
    websocket = new WebSocket(wsUri);

    websocket.onopen = function(evt) {
    	onOpen(evt, id);
    };

    websocket.onmessage = function(evt) {
    	onMessage(evt);
    };
    
    websocket.onerror = function(evt) {
        onError(evt);
    };
}

function onOpen(evt, id){
	console.log("open:"+evt.data);
	console.log(id);
    websocket.send(id);
}

function onMessage(evt) {
	console.log("message:"+evt.data);
    $('#alarm-count').html(evt.data);
}

function onError(evt) {
	console.log(evt);
}
