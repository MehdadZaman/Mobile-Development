// Name: Mehdad Zaman
// SBU ID: 112323211
//
//  FirstViewController.swift
//  Planner App
//
//  Created by user175434 on 6/14/20.
//  Copyright © 2020 mehdadzaman. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate

    var dataSourceArray = [Task]()
    
    @IBOutlet weak var tableViewFirstController1: UITableView!
    
    @IBOutlet weak var navigationTtitleCont1Bar: UINavigationBar!
    
    @IBOutlet weak var navigationTtitleCont1: UINavigationItem!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        let nib = UINib(nibName: "FirstViewController1Cell", bundle: nil)
        
        tableViewFirstController1.register(nib, forCellReuseIdentifier: "FirstViewController1Cell")
        
        tableViewFirstController1.tableFooterView = UIView(frame: .zero)

        let rect:CGRect = CGRect.init(origin: CGPoint.init(x: 0, y: 0), size: CGSize.init(width: 128, height: 64))
        
        let titleView = UIView.init(frame: rect)
        
        let label:UILabel = UILabel.init(frame: CGRect.init(x: 0, y: 0, width: 128, height: 42))
        
        let currentDate = Date()
        
        let dateFormatterPrint = DateFormatter()
        
        dateFormatterPrint.dateFormat = "MM/dd"
        let str1 = dateFormatterPrint.string(from: currentDate)
        label.text = str1
        label.font = UIFont.systemFont(ofSize: 24)
        label.textColor = UIColor.white
        label.textAlignment = .center
        titleView.addSubview(label)
        
        navigationTtitleCont1.titleView = titleView
        
        navigationTtitleCont1Bar.barTintColor = UIColor.blue
        
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return dataSourceArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableViewFirstController1.dequeueReusableCell(withIdentifier: "FirstViewController1Cell") as! FirstViewController1Cell
        
        let task = dataSourceArray[indexPath.row]
        
        cell.labelCell1.text = task.title
        cell.labelCell2.text = task.category
        
        let dateFormatterPrint = DateFormatter()
        
        dateFormatterPrint.dateFormat = "MM/dd"
        let str1 = dateFormatterPrint.string(from: task.date!)
        cell.labelCell3.text = str1
        
        dateFormatterPrint.dateFormat = "E"
        let str2 = dateFormatterPrint.string(from: task.date!)
        cell.labelCell4.text = str2
        
        return cell
    }
    
    @IBAction func addTask(_ sender: UIBarButtonItem) {
        
        var titleTextField: UITextField?
        var categoryTextField: UITextField?
        
        let dialogMessage = UIAlertController(title: "Add Task", message: "Schedule a new task\n\n\n\n\n\n\n\n\n\n\n\n", preferredStyle:  .alert)
        
        let datePicker: UIDatePicker = UIDatePicker()
        datePicker.timeZone = .autoupdatingCurrent
        
        datePicker.frame = CGRect(x: 10, y: 60, width: 250, height: 200)
        
        let add = UIAlertAction(title: "ADD", style: .default, handler:
        { (action) -> Void in
            
            let title = titleTextField?.text
            let category = categoryTextField?.text
            
            let acquiredDate = datePicker.date
            
            let dbDate = Calendar.current.date(byAdding: .hour, value: -4, to: acquiredDate)
            
            if(title != nil && category != nil) {
                self.appDelegate.insertTask(title: title!, category: category!, date: dbDate!)
                
                self.updateTableViewData()
            }
        })
        
        let cancel = UIAlertAction(title: "CANCEL", style: .cancel)
        
        dialogMessage.view.addSubview(datePicker)
        
        dialogMessage.addAction(add)
        dialogMessage.addAction(cancel)
        
        dialogMessage.addTextField { (textfield) -> Void in
            
            titleTextField = textfield
            titleTextField?.placeholder = "Task"
        }
        
        dialogMessage.addTextField { (textfield) -> Void in
            
            categoryTextField = textfield
            categoryTextField?.placeholder = "Category"
        }
        
        self.present(dialogMessage, animated: true, completion: nil)
    }
    
    func updateTableViewData() {
        
        dataSourceArray = appDelegate.fetchTasks()
        tableViewFirstController1.reloadData()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        super.viewWillAppear(animated)
        updateTableViewData()
    }
}
