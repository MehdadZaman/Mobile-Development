// Name: Mehdad Zaman
// SBU ID: 112323211
//
//  SecondViewController1Cell.swift
//  Planner App
//
//  Created by user175434 on 6/18/20.
//  Copyright © 2020 mehdadzaman. All rights reserved.
//

import UIKit

class SecondViewController1Cell: UITableViewCell {
    
    @IBOutlet weak var categoryLabel: UILabel!
    
    @IBOutlet weak var numberLabel: UILabel!
    
    @IBOutlet weak var circleView: UIView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        circleView.layer.masksToBounds = true
        
        circleView.layer.cornerRadius = circleView.bounds.width / 2
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
