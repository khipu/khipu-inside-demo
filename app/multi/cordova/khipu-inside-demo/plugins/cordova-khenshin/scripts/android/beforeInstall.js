module.exports = function(ctx) {
	var fs = require('fs'),
		path = require('path'),
		xml = ctx.requireCordovaModule('cordova-common').xmlHelpers;

	var manifestPath = path.join(ctx.opts.projectRoot, 'platforms/android/app/src/main/AndroidManifest.xml');
	var doc = xml.parseElementtreeSync(manifestPath);
	if (doc.getroot().tag !== 'manifest') {
		throw new Error(manifestPath + ' has incorrect root node name (expected "manifest")');
	}


	//write the manifest file
	fs.writeFileSync(manifestPath, doc.write({indent: 4}), 'utf-8');

};
