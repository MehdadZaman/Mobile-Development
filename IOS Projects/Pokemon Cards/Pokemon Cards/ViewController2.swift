//
//  ViewController2.swift
//  Pokemon Cards
//
//  Created by Nada Attia on 7/1/20.
//  Copyright © 2020 Mehdad Zaman. All rights reserved.
//

import UIKit

class ViewController2: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    
    
    @IBAction func typeClick(_ sender: Any) {
        UserDefaults.standard.set(1, forKey: "sort")
    }
    
    
    @IBAction func strengthClick(_ sender: Any) {
        
        UserDefaults.standard.set(2, forKey: "sort")
    }
}
