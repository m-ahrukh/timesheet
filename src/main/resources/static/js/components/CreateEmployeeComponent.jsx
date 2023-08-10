import React, { Component } from 'react'
import EmployeeService from '../services/EmployeeService';
import { Link } from 'react-router-dom';

export default class CreateEmployeeComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            firstName: "",
            lastName: "", 
            username: "", 
            password: ""
        }

        this.changeFirstNameHandler = this.changeFirstNameHandler.bind(this);
        this.changeLastNameHandler = this.changeLastNameHandler.bind(this);
        this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
        this.changePasswordHandler = this.changePasswordHandler.bind(this);
        this.saveEmployee = this.saveEmployee.bind(this)
        this.handleEvent = this.handleEvent.bind(this)
    }

    changeFirstNameHandler = (event) => {
        this.setState({firstName: event.target.value});
    }
    changeLastNameHandler = (event) => {
        this.setState({lastName: event.target.value});
    }
    changeUsernameHandler = (event) => {
        this.setState({username: event.target.value});
    }
    changePasswordHandler = (event) => {
        this.setState({password: event.target.value});
    }
    saveEmployee = (e) => {
        e.preventDefault();
        let employee = {firstName: this.state.firstName, 
                        lastName: this.state.lastName,
                        username: this.state.username,
                        password: this.state.password};
        console.log("employee => " + JSON.stringify(employee));

        EmployeeService.createEmployee(employee).then((res) => {
            window.location.replace('/employees');
        });
    }

    componentDidMount() {
        
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
                <div className='container'>
                    <div className='row'>
                        <div className='card col-md-6 offset-md-3 offset-md-3'>
                            <h3 className='text-center'>Add Employee</h3>
                            <div className='card-body'>
                                <form>
                                    <div className='form-group'>
                                        <label>First Name</label>
                                        <input placeholder='Frist Name' name='firstName' 
                                        className='form-control' value={this.state.firstName} 
                                        onChange={this.changeFirstNameHandler} />
                                    </div>
                                    <br></br>
                                    <div className='form-group'>
                                        <label>Last Name</label>
                                        <input placeholder='Last Name' name='lastName' 
                                        className='form-control' value={this.state.lastName} 
                                        onChange={this.changeLastNameHandler} />
                                    </div>
                                    <br></br>
                                    <div className='form-group'>
                                        <label>Username</label>
                                        <input placeholder='Username' name='username' 
                                        className='form-control' value={this.state.username} 
                                        onChange={this.changeUsernameHandler} />
                                    </div>
                                    <br></br>
                                    <div className='form-group'>
                                        <label>Password</label>
                                        <input placeholder='Password' name='password' type='password'
                                        className='form-control' value={this.state.password} 
                                        onChange={this.changePasswordHandler} />
                                    </div>
                                    <br></br>
                                    <button className='btn btn-success' onClick={this.saveEmployee}>Save</button>
                                    <Link to="/employees" className='btn btn-danger' style={{marginLeft: "10px"}}>Cancel</Link>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
           </div>
        )
    }
}
