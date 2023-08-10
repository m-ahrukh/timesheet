import React, { Component } from 'react'
import EmployeeService from '../services/EmployeeService';

import { useParams } from 'react-router-dom';
export function withRouter(Children) {
    return (props) => {
        const match = { params: useParams() };
        return <Children {...props} match={match} />
    }
}

class UpdateEmployeeComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            firstName: "",
            lastName: "", 
            username: "", 
            password: ""
        }

        this.changeFirstNameHandler = this.changeFirstNameHandler.bind(this);
        this.changeLastNameHandler = this.changeLastNameHandler.bind(this);
        this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
        this.changePasswordHandler = this.changePasswordHandler.bind(this);
        this.updateEmployee = this.updateEmployee.bind(this)
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
    updateEmployee = (e) => {
        e.preventDefault();
        let employee = {firstName: this.state.firstName, 
                        lastName: this.state.lastName,
                        username: this.state.username,
                        password: this.state.password};
        console.log("employee => " + JSON.stringify(employee));

        EmployeeService.updateEmployee(employee, this.state).then((res) => {
            window.location.replace('/employees');
        });

    }

    componentDidMount() {
        EmployeeService.getEmployeeById(this.state.id).then((res)=>{
            let employee = res.data;
            this.setState({firstName: employee.firstName,
                lastName: employee.lastName,
                username: employee.username,
                password: employee.password
            });
        });
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
                                    <button className='btn btn-success' onClick={this.updateEmployee}>Update</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
           </div>
        )
    }
}

export default withRouter(UpdateEmployeeComponent);