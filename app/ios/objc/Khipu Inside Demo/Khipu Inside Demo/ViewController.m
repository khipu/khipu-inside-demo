//
//  ViewController.m
//  Khipu Inside Demo
//
//  Created by Emilio Davis on 7/4/18.
//  Copyright © 2018 Emilio Davis. All rights reserved.
//

#import "ViewController.h"
#import <khenshin/khenshin.h>

@interface ViewController ()
@property (weak, nonatomic) IBOutlet UITextField *paymentId;

@end

@implementation ViewController

- (IBAction)doPay:(UIButton *)sender {
    [KhenshinInterface startEngineWithPaymentExternalId:[[self paymentId] text]
                                         userIdentifier:@""
                                      isExternalPayment:YES
                                                success:^(NSURL *returnURL){
        NSLog(@"SUCCESS");
    }
                                                failure:^(NSURL *returnURL){
        NSLog(@"FAILURE");
    }
                                               animated:NO];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
