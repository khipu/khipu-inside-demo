//
//  PaymentProcessHeader.m
//  Khipu Inside Demo
//
//  Created by Emilio Davis on 1/16/19.
//  Copyright © 2019 Emilio Davis. All rights reserved.
//

#import "PaymentProcessHeader.h"

@interface PaymentProcessHeader()

@property (weak, nonatomic) IBOutlet UIImageView *merchantShadowImageView;
@property (weak, nonatomic) IBOutlet UIImageView *merchantImageBackground;
@property (weak, nonatomic) IBOutlet UIImageView *merchantImageView;
@property (weak, nonatomic) IBOutlet UILabel *billDescriptionLabel;



@end

@implementation PaymentProcessHeader

- (void) configureWithSubject:(NSString*) subject
    formattedAmountAsCurrency:(NSString*) amount
                 merchantName:(NSString*) merchantName
             merchantImageURL:(NSString*) merchantImageURL
                paymentMethod:(NSString *) paymentMethod {
    
    NSMutableAttributedString *paying = [[NSMutableAttributedString alloc] initWithString:[NSString stringWithFormat:@"Pagando %@ a \"%@\" por %@.\rUsando %@",
                                                                                           amount,
                                                                                           merchantName,
                                                                                           subject,
                                                                                           paymentMethod
                                                                                           ]];
    
    [paying addAttribute:NSFontAttributeName
                   value:[UIFont systemFontOfSize:self.billDescriptionLabel.font.pointSize]
                   range:NSMakeRange(0, [[paying string] length] - 1)];
    [paying addAttribute:NSFontAttributeName
                   value:[UIFont boldSystemFontOfSize:[self billDescriptionLabel].font.pointSize]
                   range:[[paying string] rangeOfString:amount]];
    [paying addAttribute:NSFontAttributeName
                   value:[UIFont boldSystemFontOfSize:[self billDescriptionLabel].font.pointSize]
                   range:[[paying string] rangeOfString:subject]];
    
    [self.billDescriptionLabel setAttributedText:paying];
    
    
}

@end
