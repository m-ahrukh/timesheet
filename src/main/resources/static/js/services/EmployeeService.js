import axios from 'axios';


const  EMPLOYEE_API_BASEURL = "http://localhost:8080/employees"
class EmployeeService{

    getEmployees(){
        return axios.get(EMPLOYEE_API_BASEURL);
    }
    createEmployee(employee){
        return axios.post(EMPLOYEE_API_BASEURL, employee);
    }

    getEmployeeById(employeeId){
        return axios.get(EMPLOYEE_API_BASEURL + '/' + employeeId)
    }

    updateEmployee(employee, employeeId){
        return axios.patch(EMPLOYEE_API_BASEURL + '/' + employeeId, employee)
    }

    deleteEmployee(employeeId){
        return axios.delete(EMPLOYEE_API_BASEURL + '/' + employeeId)
    }
}

export default new EmployeeService();