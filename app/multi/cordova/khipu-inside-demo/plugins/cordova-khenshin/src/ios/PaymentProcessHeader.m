//
//  PaymentProcessHeader.m
//  Khipu
//
//  Created by Iván Galaz-Jeria on 11/11/16.
//  Copyright © 2016 Khipu. All rights reserved.
//

#import "PaymentProcessHeader.h"

@interface PaymentProcessHeader()

@end

@implementation PaymentProcessHeader

- (void) configureWithSubject:(NSString*) subject
    formattedAmountAsCurrency:(NSString*) amount
                 merchantName:(NSString*) merchantName
             merchantImageURL:(NSString*) merchantImageURL
                paymentMethod:(NSString *) paymentMethod {

    [[self amount] setText:[NSString stringWithFormat:@"%@",amount]];
    [[self subject] setText:[NSString stringWithFormat:@"%@",subject]];
    [self downloadMerchantImageWithMerchantImageURL:merchantImageURL];
}

- (void)downloadMerchantImageWithMerchantImageURL:(NSString*) pictureURL {
    NSURL *url = [self safeURLWithString:pictureURL];
    NSData *data = [NSData dataWithContentsOfURL:url];
    UIImage *img = [[UIImage alloc] initWithData:data];
    [self.merchantImage setImage:img];
}

- (NSURL *) safeURLWithString:(NSString *)URLString {

    return [NSURL URLWithString:[URLString stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]]];
}
@end
