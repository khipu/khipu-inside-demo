//
//  PaymentProcessHeader.swift
//  Khipu Inside Demo
//
//  Created by Emilio Davis on 1/25/19.
//  Copyright © 2019 Emilio Davis. All rights reserved.
//

import Foundation
import UIKit
import khenshin

class PaymentProcessHeader: UIView, ProcessHeader {
    
    @IBOutlet weak var billDescriptionLabel: UILabel!
    
    func configure(withSubject subject: String!, formattedAmountAsCurrency amount: String!, merchantName: String!, merchantImageURL: String!, paymentMethod: String!) {
        let paying = NSMutableAttributedString(string: String(format: "Pagando %@ a \"%@\" por %@.\rUsando %@", amount, merchantName, subject, paymentMethod))
        
        
        billDescriptionLabel.attributedText = paying
    }
    
    
}
