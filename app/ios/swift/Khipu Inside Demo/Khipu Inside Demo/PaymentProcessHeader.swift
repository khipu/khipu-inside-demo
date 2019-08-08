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
        
        var paying = String(format: "Pagando %@ a \"%@\" por %@.", amount, merchantName, subject)
        if(paymentMethod != nil) {
            paying = String(format: "\n%@ Usando %@.", paying, paymentMethod)
        }
        
        billDescriptionLabel.attributedText = NSMutableAttributedString(string: paying)
    }
    
    
}
