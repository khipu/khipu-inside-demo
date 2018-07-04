//
//  ViewController.swift
//  Khipu Inside Demo
//
//  Created by Emilio Davis on 7/4/18.
//  Copyright © 2018 Emilio Davis. All rights reserved.
//

import UIKit
import khenshin

class ViewController: UIViewController {

    @IBOutlet weak var paymentId: UITextField!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func doPay(_ sender: UIButton) {
        
        KhenshinInterface.startEngine(withPaymentExternalId: paymentId.text, userIdentifier: "", isExternalPayment: true, success: { (exitURL: URL?) in
            NSLog("SUCCESS")
        }, failure: { (exitURL: URL?) in
            NSLog("FAILURE")
        }, animated: false)
        
    }
    
}

