//
//  PaymentProcessHeader.h
//  Khipu
//
//  Created by Iván Galaz-Jeria on 11/11/16.
//  Copyright © 2016 Khipu. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <khenshin/khenshin.h>

@interface PaymentProcessHeader : UIView<ProcessHeader>

@property (weak, nonatomic) IBOutlet UILabel *subject;
@property (weak, nonatomic) IBOutlet UILabel *amount;
@property (weak, nonatomic) IBOutlet UILabel *merchantName;
@property (weak, nonatomic) IBOutlet UILabel *paymentMethod;
@property (weak, nonatomic) IBOutlet UIImageView *merchantImage;


@end
