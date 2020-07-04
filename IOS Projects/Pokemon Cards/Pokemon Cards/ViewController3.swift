//
//  ViewController3.swift
//  Pokemon Cards
//
//  Created by Nada Attia on 7/1/20.
//  Copyright © 2020 Mehdad Zaman. All rights reserved.
//

import UIKit

class ViewController3: UIViewController, UITableViewDataSource, UITableViewDelegate {
        
    let appDelegate = UIApplication.shared.delegate as! AppDelegate

    var dataSourceArray = [PokemonCard]()
    
    @IBOutlet weak var tblview: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        let nib = UINib(nibName: "cell3", bundle: nil)
        
        tblview.register(nib, forCellReuseIdentifier: "cell3")
        
        self.updateTableViewData()
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return dataSourceArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tblview.dequeueReusableCell(withIdentifier: "cell3") as! cell3
        
        let task = dataSourceArray[indexPath.row]
        
        cell.nameLabel.text = task.name
        
        if((UserDefaults.standard.integer(forKey: "sort")) == 1) {
            cell.typeLabel.text = task.type ?? ""
        }
        else {
            cell.typeLabel.text = "\(task.strength)"
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        tableView.deselectRow(at: indexPath, animated: true)
        
        let pk = dataSourceArray[indexPath.row]
        
        let message = "name: \(pk.name ?? "")\n generation: \(pk.generation ?? "")\n national Number: \(pk.nationalNumber)\n strength: \(pk.strength)\n type: \(pk.type ?? "")\n"
        
        
        
        let dialogMessage = UIAlertController(title: "Pokemon Card", message: message, preferredStyle:  .alert)
        
        
        let cancel = UIAlertAction(title: "OK", style: .cancel)
        
        dialogMessage.addAction(cancel)
        
        self.present(dialogMessage, animated: true, completion: nil)
    }
    
    
    func updateTableViewData() {
        
        if((UserDefaults.standard.integer(forKey: "sort")) == 1) {
            dataSourceArray = appDelegate.fetchCards()
            dataSourceArray.sort(by: {($0.type ?? "") < ($1.type ?? "")})
            tblview.reloadData()
        }
        else {
            dataSourceArray = appDelegate.fetchCards()
            dataSourceArray.sort(by: {$0.strength < $1.strength})
            tblview.reloadData()
        }

    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        super.viewWillAppear(animated)
        updateTableViewData()
    }

}
