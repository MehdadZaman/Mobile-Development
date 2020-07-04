//
//  ViewController.swift
//  Pokemon Cards
//
//  Created by Nada Attia on 7/1/20.
//  Copyright © 2020 Mehdad Zaman. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITableViewDelegate {
    
    @IBOutlet weak var nameField: UITextField!
    
    @IBOutlet weak var typeField: UITextField!

    @IBOutlet weak var npnField: UITextField!
    
    @IBOutlet weak var generationField: UITextField!
    
    @IBOutlet weak var strengthField: UITextField!
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    
    @IBAction func addCard(_ sender: Any) {
        
        let name = nameField.text
        let type = typeField.text
        let npn = Int64(npnField.text ?? "-1")!
        let generation = generationField.text
        let strength = Int64(strengthField.text ?? "-1")!

        
        appDelegate.insertCard(name: name ?? "", type: type ?? "", generation: generation ?? "", nationalNumber: npn, strength: strength)
        
    }
}

