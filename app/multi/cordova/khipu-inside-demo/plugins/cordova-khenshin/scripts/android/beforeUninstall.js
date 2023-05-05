module.exports = function(ctx) {
	var fs = require('fs'),
		path = require('path');

	var colorsPath = path.join(ctx.opts.projectRoot, 'platforms/android/app/src/main/res/values/khenshincolors.xml');

	fs.unlinkSync(colorsPath);

};
