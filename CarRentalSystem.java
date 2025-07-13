package car_rental_system;

import java.util.*;

public class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;              // Active rentals
    private List<Rental> completedRentals;     // Returned/completed rentals

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
        completedRentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            completedRentals.add(rentalToRemove);
            System.out.println("Car returned successfully.");
        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\n==== CAR RENTAL SYSTEM ====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. View Active Rentals");
            System.out.println("4. View Completed Rental History");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scan.nextInt();
            scan.nextLine();

            if (choice == 1) {
                System.out.println("\n== Rent a Car ==");
                System.out.print("Enter your name: ");
                String customerName = scan.nextLine();

                System.out.println("\nAvailable Cars:");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }

                System.out.print("\nEnter the Car ID you want to rent: ");
                String carId = scan.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scan.nextInt();
                scan.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equalsIgnoreCase(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==");
                    System.out.println("Customer ID : " + newCustomer.getCustomerId());
                    System.out.println("Customer Name : " + newCustomer.getName());
                    System.out.println("Car : " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days : " + rentalDays);
                    System.out.printf("Total Price : ₹%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scan.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.print("Enter Payment Mode (Cash/UPI/Card): ");
                        String paymentMode = scan.nextLine();
                        Payment payment = new Payment(new Rental(selectedCar, newCustomer, rentalDays), paymentMode);
                        payment.generateReceipt();

                        System.out.println( Car rented successfully!");
                    } else {
                        System.out.println(" Rental cancelled.");
                    }
                } else {
                    System.out.println(" Invalid car selection or car not available.");
                }

            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==");
                System.out.print("Enter Car ID to return: ");
                String returnCarId = scan.nextLine();

                Car returnCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equalsIgnoreCase(returnCarId) && !car.isAvailable()) {
                        returnCar = car;
                        break;
                    }
                }

                if (returnCar != null) {
                    returnCar(returnCar);
                } else {
                    System.out.println("❌ Invalid Car ID or the car is not currently rented.");
                }

            } else if (choice == 3) {
                System.out.println("\n== Active Rentals ==");
                if (rentals.isEmpty()) {
                    System.out.println("No active rentals found.");
                } else {
                    for (Rental rental : rentals) {
                        System.out.println("Customer: " + rental.getCustomer().getName() +
                                " | Car: " + rental.getCar().getBrand() + " " + rental.getCar().getModel() +
                                " | Days: " + rental.getDays());
                    }
                }

            } else if (choice == 4) {
                System.out.println("\n== Completed Rental History ==");
                if (completedRentals.isEmpty()) {
                    System.out.println("No completed rentals yet.");
                } else {
                    for (Rental rental : completedRentals) {
                        System.out.println("Customer: " + rental.getCustomer().getName() +
                                " | Car: " + rental.getCar().getBrand() + " " + rental.getCar().getModel() +
                                " | Days: " + rental.getDays());
                    }
                }

            } else if (choice == 5) {
                System.out.println("🚗 Thank you for using the Car Rental System!");
                break;
            } else {
                System.out.println("❌ Invalid choice. Please try again.");
            }
        }
        scan.close();
    }

    public static void main(String[] args) {
        CarRentalSystem system = new CarRentalSystem();
        system.addCar(new Car("C001", "TOYOTA", "CAMRY", 90.0));
        system.addCar(new Car("C002", "HONDA", "CIVIC", 80.0));
        system.addCar(new Car("C003", "FORD", "FOCUS", 70.0));

        system.menu();
    }
}