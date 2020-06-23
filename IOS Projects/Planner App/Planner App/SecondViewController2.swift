// Name: Mehdad Zaman
// SBU ID: 112323211
//
//  SecondViewController2.swift
//  Planner App
//
//  Created by user175434 on 6/18/20.
//  Copyright © 2020 mehdadzaman. All rights reserved.
//

import UIKit

class SecondViewController2: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    var categoryString = String()
    
    var filteredTasks = [Task]()
    
    let appDelegate = UIApplication.shared.delegate as! AppDelegate

    var dataSourceArray = [Task]()
    
    @IBOutlet weak var imageButton: UIImageView!
    
    @IBOutlet weak var secondViewTableView2: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        navigationController?.navigationBar.barTintColor = UIColor.blue
        
        navigationItem.title = categoryString
        
        navigationController?.navigationBar.titleTextAttributes = [.foregroundColor: UIColor.white]
        
        
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(SecondViewController2.imageTapped(gesture:)))
        
        imageButton.addGestureRecognizer(tapGesture)
        imageButton.isUserInteractionEnabled = true
        
        imageButton.layer.masksToBounds = true
        
        imageButton.layer.cornerRadius = (imageButton.bounds.width) / 2;
        
        imageButton.backgroundColor = UIColor.blue
        
        secondViewTableView2.tableFooterView = UIView(frame: .zero)
        
        let nib = UINib(nibName: "FirstViewController1Cell", bundle: nil)
        
        secondViewTableView2.register(nib, forCellReuseIdentifier: "FirstViewController1Cell")
        
        updateTableViewData()
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    
    @objc func imageTapped(gesture: UIGestureRecognizer) {
        
        var titleTextField: UITextField?
        
        let dialogMessage = UIAlertController(title: "Add Task", message: "Schedule a new task\n\n\n\n\n\n\n\n\n\n\n\n", preferredStyle:  .alert)
        
        let datePicker: UIDatePicker = UIDatePicker()
        datePicker.timeZone = .autoupdatingCurrent
        
        datePicker.frame = CGRect(x: 10, y: 60, width: 250, height: 200)
        
        let add = UIAlertAction(title: "ADD", style: .default, handler:
        { (action) -> Void in
            
            let title = titleTextField?.text
            
            let acquiredDate = datePicker.date
            
            let dbDate = Calendar.current.date(byAdding: .hour, value: -4, to: acquiredDate)
            
            if(title != nil) {
                self.appDelegate.insertTask(title: title!, category: self.categoryString, date: dbDate!)
                
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
        
        self.present(dialogMessage, animated: true, completion: nil)
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return filteredTasks.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = secondViewTableView2.dequeueReusableCell(withIdentifier: "FirstViewController1Cell") as! FirstViewController1Cell
        
        let task = filteredTasks[indexPath.row]
        
        cell.labelCell1.text = task.title
        cell.labelCell2.text = ""
        
        let dateFormatterPrint = DateFormatter()
        
        dateFormatterPrint.dateFormat = "MM/dd"
        let str1 = dateFormatterPrint.string(from: task.date!)
        cell.labelCell3.text = str1
        
        dateFormatterPrint.dateFormat = "E"
        let str2 = dateFormatterPrint.string(from: task.date!)
        cell.labelCell4.text = str2
        
        return cell
    }
    
    func updateTableViewData() {
        
        dataSourceArray = appDelegate.fetchTasks()
        
        filteredTasks = [Task]()
        
        for tempTask in dataSourceArray {
            if(tempTask.category == categoryString) {
                filteredTasks.append(tempTask)
            }
        }
        
        secondViewTableView2.reloadData()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        super.viewWillAppear(animated)
        updateTableViewData()
    }
    
}
