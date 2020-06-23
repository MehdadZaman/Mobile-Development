// Name: Mehdad Zaman
// SBU ID: 112323211
//
//  ViewController.swift
//  Tip Calculator App
//
//  Created by user175434 on 6/12/20.
//  Copyright © 2020 mehdadzaman. All rights reserved.
///Users/user175434/Desktop/Tip Calculator App/Tip Calculator App/Base.lproj/Main.storyboard

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var tipAmountLabel: UILabel!
    
    @IBOutlet weak var billAmountText: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    
    @IBAction func fifteenPercent(_ sender: Any) {
        
        let inputText = billAmountText.text
        if(inputText == nil || inputText!.isEmpty) {
            tipAmountLabel.text = "Must input a value"
            
            return
        }
        
        if let numberValue = Double(inputText!) {
            tipAmountLabel.text =  "Tip Amount: $" + String(format: "%.2f", (numberValue * 0.15)) + " Total: $" + String(format: "%.2f", (numberValue * 1.15))
            
            return
        }
        
        tipAmountLabel.text = "Must input a valid value"
    }
    

    @IBAction func eighteenPercent(_ sender: Any) {
        
        let inputText = billAmountText.text
        if(inputText == nil || inputText!.isEmpty) {
            tipAmountLabel.text = "Must input a value"
            
            return
        }
        
        if let numberValue = Double(inputText!) {
        tipAmountLabel.text =  "Tip Amount: $" + String(format: "%.2f", (numberValue * 0.18)) + " Total: $" + String(format: "%.2f", (numberValue * 1.18))
            
            return
        }
        
        tipAmountLabel.text = "Must input a valid value"
    }
    
    
    @IBAction func twentyPercent(_ sender: Any) {
        
        let inputText = billAmountText.text
        if(inputText == nil || inputText!.isEmpty) {
            tipAmountLabel.text = "Must input a value"
            
            return
        }
        
        if let numberValue = Double(inputText!) {
        tipAmountLabel.text =  "Tip Amount: $" + String(format: "%.2f", (numberValue * 0.20)) + " Total: $" + String(format: "%.2f", (numberValue * 1.20))
            
            return
        }
        
        tipAmountLabel.text = "Must input a valid value"
    }
    
}
