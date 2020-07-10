var socket = null;
	
$(document).ready(function (){
	if(loginID)
		send_alarms();
});

function send_alarms() {
    var wsUri = "ws://localhost:8080/echo";
    websocket = new WebSocket(wsUri);

    websocket.onopen = function(evt) {
    	onOpen(evt);
    };

    websocket.onmessage = function(evt) {
    	onMessage(evt);
    };
    
    websocket.onerror = function(evt) {
        onError(evt);
    };
}

function onOpen(evt){
	console.log("open:"+evt.data);
    websocket.send(loginID);
}

function onMessage(evt) {
	console.log("message:"+evt.data);
    $('#alarm-count').html(evt.data);
}

function onError(evt) {
	console.log(evt);
}
