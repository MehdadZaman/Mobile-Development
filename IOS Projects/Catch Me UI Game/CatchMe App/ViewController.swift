// Name: Mehdad Zaman
// SBU ID: 112323211
//
//  ViewController.swift
//  CatchMe App
//
//  Created by user175434 on 6/14/20.
//  Copyright © 2020 mehdadzaman. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    var userScore = 0
    
    @IBOutlet weak var catchMeButton: UIButton!
    
    @IBOutlet weak var endGameView: UIView!
    
    @IBOutlet weak var scoreView: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        catchMeButton.layer.masksToBounds = true
        
        catchMeButton.layer.cornerRadius = catchMeButton.bounds.width / 2
        
        let halfWidth = catchMeButton.bounds.width / 2.0
        
        let halfHeight = catchMeButton.bounds.height / 2.0
        
        let minX = view.safeAreaInsets.left
        
        let maxX = view.bounds.width - view.safeAreaInsets.right
        
        let minY = view.safeAreaInsets.top
        
        let maxY = view.bounds.height - view.safeAreaInsets.bottom
        catchMeButton.center = .init(x: CGFloat.random(in: (minX + halfWidth) ... (maxX - halfWidth)), y: CGFloat.random(in: (minY + halfHeight) ... (maxY - halfHeight)))
        
        setEndGame()
    }
    
    func setEndGame() {
        
        endGameView.layer.masksToBounds = true
        
        endGameView.layer.cornerRadius = endGameView.bounds.width / 2
        
        let halfWidth = endGameView.bounds.width / 2.0
        
        let halfHeight = endGameView.bounds.height / 2.0
        
        let minX = view.safeAreaInsets.left
        
        let maxX = view.bounds.width - view.safeAreaInsets.right
        
        let minY = view.safeAreaInsets.top
        
        let maxY = view.bounds.height - view.safeAreaInsets.bottom
        
        endGameView.center.x = CGFloat.random(in: (minX + halfWidth) ... (maxX - halfWidth))
        
        endGameView.center.y = CGFloat.random(in: (minY + halfHeight) ... (maxY - halfHeight))
        
    }
    
    
    @IBAction func catchMe(_ sender: Any) {
        
        let distanceValue = (pow((catchMeButton.center.x - endGameView.center.x), 2) + pow((catchMeButton.center.y - endGameView.center.y), 2)).squareRoot()
        
        if(distanceValue <= ((catchMeButton.bounds.width / 2.0) + (endGameView.bounds.width / 2.0))) {
            
            if(userScore == 0)
            {
                userScore = 1
            }
            else
            {
                createAlert (title:"The Game Has Ended!", message: "You have had \(userScore) successful taps! Press OK to play again.")
                
                userScore = 0
            }
            
            setEndGame()
        }
        else
        {
            userScore += 1
        }
        
        let halfWidth = catchMeButton.bounds.width / 2.0
        
        let halfHeight = catchMeButton.bounds.height / 2.0
        
        let minX = view.safeAreaInsets.left
        
        let maxX = view.bounds.width - view.safeAreaInsets.right
        
        let minY = view.safeAreaInsets.top
        
        let maxY = view.bounds.height - view.safeAreaInsets.bottom
        catchMeButton.center = .init(x: CGFloat.random(in: (minX + halfWidth) ... (maxX - halfWidth)), y: CGFloat.random(in: (minY + halfHeight) ... (maxY - halfHeight)))
        
        scoreView.text = "Score \(userScore)"    }
    
    func createAlert (title:String, message:String)
    {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertController.Style.alert)
        
        alert.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        
        self.present(alert, animated: true, completion: nil)
    }
}
