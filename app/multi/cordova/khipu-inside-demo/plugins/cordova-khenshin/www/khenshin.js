module.exports = {
	startByPaymentId: function (params, successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "Khenshin", "startByPaymentId", [params]);
	},
	startByAutomatonId: function (params, successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "Khenshin", "startByAutomatonId", params);
	},
	createPayment: function (params, successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "Khenshin", "createPayment", params);
	}
};