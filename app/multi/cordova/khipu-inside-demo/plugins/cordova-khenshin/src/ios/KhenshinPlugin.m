#import "KhenshinPlugin.h"
#import <Cordova/CDVPlugin.h>
#import <khenshin/khenshin.h>
#import "PaymentProcessHeader.h"
#import "AFNetworking.h"

@implementation KhenshinPlugin

- (void)pluginInitialize
{
        [KhenshinInterface initWithBuilderBlock:^(KhenshinBuilder *builder){
        builder.APIUrl = @"https://khipu.com/app/enc/";
        builder.barCenteredLogo = [UIImage imageNamed:@"Bar Logo"];
        builder.barLeftSideLogo = [[UIImage alloc] init];
        builder.processHeader = [self processHeader];
        builder.skipExitPage = NO;
        builder.keepCookies = YES;
        builder.mainButtonStyle = KHMainButtonFatOnForm;

        builder.cellPadding = 30;
        builder.hideWebAddressInformationInForm = TRUE;

        builder.cellSeparatorHeight = 2.f;
        builder.barTintColor = [UIColor whiteColor];
        builder.navigationBarTextTint = [UIColor cyanColor];
        builder.textColorHex = @"#ff00ff";
        builder.principalColor = [UIColor lightGrayColor];
        builder.secondaryColor = [UIColor redColor];
        builder.darkerPrincipalColor = [UIColor darkGrayColor];

        if (@available(iOS 15.0, *)) {
                UINavigationBarAppearance *appearance = [UINavigationBarAppearance new];
                [appearance configureWithOpaqueBackground];
                [[UINavigationBar appearance] setStandardAppearance:appearance];
                [[UINavigationBar appearance] setScrollEdgeAppearance:appearance];
        }

        builder.allowCredentialsSaving = YES;
    }];
}

- (UIView*) processHeader {

    PaymentProcessHeader *processHeaderObj =    [[[NSBundle mainBundle] loadNibNamed:@"PaymentProcessHeader"
                                                                               owner:self
                                                                             options:nil]
                                                 objectAtIndex:0];
    return processHeaderObj;
}

- (void):(NSNotification *)notification
{
    [[NSUserDefaults standardUserDefaults] setBool:NO
                                            forKey:@"KH_SHOW_HOW_IT_WORKS"];
    [[NSUserDefaults standardUserDefaults] synchronize];
}

- (void)startByPaymentId:(CDVInvokedUrlCommand*)command
{


    [KhenshinInterface startEngineWithPaymentExternalId:[command.arguments objectAtIndex:0]
                                         userIdentifier:@""
                                      isExternalPayment:true
                                                success:^(NSURL *returnURL) {
                                                    CDVPluginResult* pluginResult = nil;
                                                    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
                                                    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                                                }
                                                failure:^(NSURL *returnURL) {
                                                    CDVPluginResult* pluginResult = nil;
                                                    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
                                                    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                                                }
                                               animated:false];
}

- (void)startByAutomatonId:(CDVInvokedUrlCommand*)command
{

    NSMutableDictionary* parameters = [[NSMutableDictionary alloc] initWithCapacity:20];
    for(int i = 1; i < [command.arguments count] ; i ++) {
        NSArray* kv = [[command.arguments objectAtIndex:i] componentsSeparatedByString:@":"];
        [parameters setObject:[kv objectAtIndex:1] forKey:[kv objectAtIndex:0]];
    }

    [KhenshinInterface startEngineWithAutomatonId:[command.arguments objectAtIndex:0]
                                         animated:false
                                       parameters:parameters
                                   userIdentifier:@""
                                          success:^(NSURL *returnURL) {
                                              CDVPluginResult* pluginResult = nil;
                                              pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
                                              [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                                          }
                                          failure:^(NSURL *returnURL) {
                                              CDVPluginResult* pluginResult = nil;
                                              pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
                                              [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                                          }];

}

@end
