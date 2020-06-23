// Name: Mehdad Zaman
// SBU ID: 112323211
//
//  SecondViewController.swift
//  Planner App
//
//  Created by user175434 on 6/14/20.
//  Copyright © 2020 mehdadzaman. All rights reserved.
//

import UIKit

class SecondViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    var categoryNames = [String]()
    var categoryCounts = [String : Int]()
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate

    var dataSourceArray = [Task]()
    
    @IBOutlet weak var tableViewFirstController2: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        navigationController?.navigationBar.barTintColor = UIColor.blue
        
        navigationItem.title = "Classes"
        
        navigationController?.navigationBar.titleTextAttributes = [.foregroundColor: UIColor.white]
        
        let nib = UINib(nibName: "SecondViewController1Cell", bundle: nil)
        
        tableViewFirstController2.register(nib, forCellReuseIdentifier: "SecondViewController1Cell")
        
        tableViewFirstController2.tableFooterView = UIView(frame: .zero)
        
        updateTableViewData()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return categoryNames.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableViewFirstController2.dequeueReusableCell(withIdentifier: "SecondViewController1Cell") as! SecondViewController1Cell
        
        let task = categoryNames[indexPath.row]
        
        cell.categoryLabel.text = task
        cell.numberLabel.text = "\(categoryCounts[task] ?? 0)"
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        tableViewFirstController2.deselectRow(at: indexPath, animated: true)
        
        performSegue(withIdentifier: "selectCategory", sender: categoryNames[indexPath.row])
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let svc = segue.destination as! SecondViewController2
        
        svc.categoryString = sender as! String
    }
    
    func updateTableViewData() {
        
        dataSourceArray = appDelegate.fetchTasks()
        
        categoryNames = [String]()
        categoryCounts = [String : Int]()
        
        for tempTask in dataSourceArray {
            if(categoryNames.contains(tempTask.category!)) {
                categoryCounts[tempTask.category!]  = categoryCounts[tempTask.category!]! + 1
            }
            else {
                categoryNames.append(tempTask.category!)
                categoryCounts[tempTask.category!] = 1
                
            }
        }
        
        tableViewFirstController2.reloadData()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        super.viewWillAppear(animated)
        updateTableViewData()
    }
}

