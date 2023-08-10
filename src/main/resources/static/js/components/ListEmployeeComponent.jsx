import React, { Component } from 'react'
import EmployeeService from '../services/EmployeeService'

export default class ListEmployeeComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            employees: []
        }
        
        this.addEmployee = this.addEmployee.bind(this)
        this.editEmployee = this.editEmployee.bind(this)
        this.deleteEmployee = this.deleteEmployee.bind(this)
        this.handleEvent = this.handleEvent.bind(this)
    }

    componentDidMount() {
        EmployeeService.getEmployees().then((res) =>{
            this.setState({employees: res.data})
        });
    }

    addEmployee(){
        window.location.replace('/add-employee');
        // this.props.history.push('/add-employee');
    }

    editEmployee(id){
        window.location.replace(`/update-employee/${id}`);
        //this.props.history.push(`/update-employee/${id}`)
    }

    deleteEmployee(id){
        EmployeeService.deleteEmployee(id).then((res)=> {
            this.setState({employees: this.state.employees.filter(employee => employee.id !== id)});
        })
    }
    
    componentDidUpdate(prevProps, prevState, snapshot) { if (prevState.name !== this.state.name) { this.handler() } }
    componentWillUnmount() {
        
    }

    // Prototype methods, Bind in Constructor (ES2015)
    handleEvent() {}

    // Class Properties (Stage 3 Proposal)
    handler = () => { this.setState() }

    render() {
        return (
            <div>
                <h2 className='text-center'>Employees List</h2>
                <button className='btn btn-primary' onClick={this.addEmployee}>Add Employee</button>
                <div className='row'>
                    <table className='table table-striped table-bordered'>
                        <thead>
                            <tr>
                                <th>Employee ID</th>
                                <th>Employee First Name</th>
                                <th>Employee Last Name</th>
                                <th>Employee Username</th>
                                <th>Actions</th>
                            </tr>
                        </thead>

                        <tbody>
                            {
                                this.state.employees.map(
                                    employee => 
                                    <tr key = {employee.id}>
                                        <td>{employee.id}</td>
                                        <td>{employee.firstName}</td>
                                        <td>{employee.lastName}</td>
                                        <td>{employee.username}</td>
                                        <td>
                                            <button onClick={ () => {this.editEmployee(employee.id)}} className='btn btn-info' >Update</button>
                                            <button style={{marginLeft: "10px"}} onClick={ () => {this.deleteEmployee(employee.id)}} className='btn btn-danger' >Delete</button>
                                        </td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}
