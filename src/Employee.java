//POJO class for database columns

class Employee {
 private String name;
 private String phone;
 private String email;
 private String password;

 // Getter and Setters

 public void setName(String name) {
  this.name = name;
 }

 public void setPhone(String phone) {
  this.phone = phone;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public void setPassword(String password) {
  this.password = password;
 }

 public String getName() {
  return name;
 }

 public String getPhone() {
  return phone;
 }

 public String getEmail() {
  return email;
 }

 public String getPassword() {
  return password;
 }

}