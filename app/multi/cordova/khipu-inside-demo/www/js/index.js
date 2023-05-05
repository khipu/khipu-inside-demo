function startByPaymentId(operationId) {
    khenshin.startByPaymentId(operationId, success => console.log(success), err => console.log(err));
}

function bindClick(elementId, callbackFunction) {
    document
        .getElementById(elementId)
        .addEventListener("click", callbackFunction);
}
function onDeviceReady() {
    console.log("Running cordova-" + cordova.platformId + "@" + cordova.version);
    document.getElementById("deviceready").classList.add("ready");
    bindClick("pagar", () => startByPaymentId('ID DEL PAGO'));
}

document.addEventListener("deviceready", onDeviceReady, false);
