// Name: Mehdad Zaman
// SBU ID: 112323211
//
//  AppDelegate.swift
//  Planner App
//
//  Created by user175434 on 6/14/20.
//  Copyright © 2020 mehdadzaman. All rights reserved.
//

import UIKit
import CoreData

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }
    
    func insertTask(title : String, category : String, date : Date) {
        
        let task = Task(context: persistentContainer.viewContext)
        
        task.title = title
        task.category = category
        task.date = date
        
        saveContext()
    }
    
    func fetchTasks() -> [Task] {
        
        var taskArray = [Task]()
        
        let fetchRequest = NSFetchRequest<Task>(entityName: "Task")
        
        do {
            taskArray = try persistentContainer.viewContext.fetch(fetchRequest)
            }
            catch {
                print(error)
            }
        
            return taskArray
        }
    
    func deleteTask(task : Task) {
        persistentContainer.viewContext.delete(task)
        saveContext()
    }
    
    func updateTask(task: Task, title : String, category : String, date : Date) {
        
        task.title = title
        task.category = category
        task.date = date
        
        saveContext()
        
    }
    
    
    /* func acquireCategories() -> NSDictionary {
        
        var taskArray2 = [Task]()
        
        let keypathExp = NSExpression(forKeyPath: "category") // can be any column
        let expression = NSExpression(forFunction: "count:", arguments: [keypathExp])

        let countDesc = NSExpressionDescription()
        countDesc.expression = expression
        countDesc.name = "count"
        countDesc.expressionResultType = .integer64AttributeType
        
        let request = NSFetchRequest<Task>(entityName: "Task")
        request.returnsObjectsAsFaults = false
        request.propertiesToGroupBy = ["category"]
        request.propertiesToFetch = ["category", countDesc]
        request.resultType = .dictionaryResultType
        
        
         do {
                taskArray2 = try persistentContainer.viewContext.fetch(request)
            }
            catch {
                print(error)
            }
        
    } */

    // MARK: - Core Data stack

    lazy var persistentContainer: NSPersistentContainer = {
        /*
         The persistent container for the application. This implementation
         creates and returns a container, having loaded the store for the
         application to it. This property is optional since there are legitimate
         error conditions that could cause the creation of the store to fail.
        */
        let container = NSPersistentContainer(name: "Planner_App")
        container.loadPersistentStores(completionHandler: { (storeDescription, error) in
            if let error = error as NSError? {
                // Replace this implementation with code to handle the error appropriately.
                // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                 
                /*
                 Typical reasons for an error here include:
                 * The parent directory does not exist, cannot be created, or disallows writing.
                 * The persistent store is not accessible, due to permissions or data protection when the device is locked.
                 * The device is out of space.
                 * The store could not be migrated to the current model version.
                 Check the error message to determine what the actual problem was.
                 */
                fatalError("Unresolved error \(error), \(error.userInfo)")
            }
        })
        return container
    }()

    // MARK: - Core Data Saving support

    func saveContext () {
        let context = persistentContainer.viewContext
        if context.hasChanges {
            do {
                try context.save()
            } catch {
                // Replace this implementation with code to handle the error appropriately.
                // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                let nserror = error as NSError
                fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
            }
        }
    }

}

