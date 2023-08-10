import React, { Component } from 'react'

import { Link } from 'react-router-dom';

import { useParams } from 'react-router-dom';
import TemplateService from '../services/TemplateService';

export function withRouter(Children) {
    return (props) => {
        const match = { params: useParams() };
        return <Children {...props} match={match} />
    }
}
export default class UpdateTemplateComponent extends Component {
    constructor(props) {
        super(props)

        const {employeeId, id} = this.props;
        this.state = {
            employeeId: employeeId,
            id: id,
            templateDay: "",
            daysOptions: ["select a day", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"],
            checkIn: "",
            checkOut: "",
            lunchBreakStart: "",
            lunchBreakEnd: ""
        }

        this.changeDayHandler = this.changeDayHandler.bind(this);
        this.changeCheckInHandler = this.changeCheckInHandler.bind(this);
        this.changeCheckOutHandler = this.changeCheckOutHandler.bind(this);
        this.changeLunchBreakStartHandler = this.changeLunchBreakStartHandler.bind(this);
        this.changeLunchBreakEndHandler = this.changeLunchBreakEndHandler.bind(this);
        this.saveEmployee = this.saveEmployee.bind(this)
        this.handleEvent = this.handleEvent.bind(this)
    }

    changeDayHandler = (event) => {
        this.setState({templateDay: event.target.value});
    }
    changeCheckInHandler = (event) => {
        this.setState({checkIn: event.target.value});
    }
    changeCheckOutHandler = (event) => {
        this.setState({checkOut: event.target.value});
    }
    changeLunchBreakStartHandler = (event) => {
        this.setState({lunchBreakStart: event.target.value});
        
    }
    changeLunchBreakEndHandler = (event) => {
        this.setState({lunchBreakEnd: event.target.value});
    }
    saveEmployee = (e) => {
        e.preventDefault();

        const checkInObj = new Date(`2023-08-04T${this.state.checkIn}`).toLocaleTimeString([], {hour12: false, hour: '2-digit', minute: '2-digit', second:'2-digit'});
        const checkOutObj = new Date(`2023-08-04T${this.state.checkOut}`).toLocaleTimeString([], {hour12: false,  hour: '2-digit', minute: '2-digit', second:'2-digit' });
        const breakStartObj = new Date(`2023-08-04T${this.state.lunchBreakStart}`).toLocaleTimeString([], {hour12: false,  hour: '2-digit', minute: '2-digit', second:'2-digit' });
        const breakEndObj = new Date(`2023-08-04T${this.state.lunchBreakEnd}`).toLocaleTimeString([], {hour12: false, hour: '2-digit', minute: '2-digit', second:'2-digit' });

        let template = {templateDay: this.state.templateDay, 
                        checkIn: checkInObj,
                        checkOut: checkOutObj,
                        lunchBreakStart: breakStartObj,
                        lunchBreakEnd: breakEndObj};

        console.log("template => " + JSON.stringify(template));

        TemplateService.updateTemplate(template, this.state.employeeId, this.state.id).then((res) => {
            window.location.replace(`/${this.state.employeeId}/get-templates`);
        });
    }

    componentDidMount() {
        console.log("in did mont function")
        console.log("props" + this.props)
        console.log(this.state.employeeId + "  " + this.state.id)
        TemplateService.getTemplateById(this.state.employeeId, this.state.id).then((res)=>{
            let template = res.data;
            this.setState({templateDay:template.templateDay,
            checkIn: template.checkIn,
            checkOut: template.checkOut,
            lunchBreakStart: template.lunchBreakStart,
            lunchBreakEnd: template.lunchBreakEnd
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
        const {daysOptions} = this.state;
        return (
            <div>
            <div className='container'>
                <div className='row'>
                    <div className='card col-md-6 offset-md-3 offset-md-3'>
                        <h3 className='text-center'>Update Template</h3>
                        <div className='card-body'>
                            <form>
                                <div className='form-group'>
                                    <label htmlFor='options'>Template Day</label>
                                    <br></br>
                                    <select value={this.state.templateDay} onChange={this.changeDayHandler}>
                                        {daysOptions.map((val, index) =>{
                                            return <option key={index} value={val}>{val.charAt(0).toUpperCase() + val.slice(1)}</option>
                                        })}
                                    </select>
                                </div>
                                <br></br>
                                <div className='form-group'>
                                    <label>Check In</label>
                                    <input placeholder='Check In Time' name='checkIn' value={this.state.checkIn} 
                                        onChange={this.changeCheckInHandler} className='form-control' type='time' />
                                </div>
                                <br></br>
                                <div className='form-group'>
                                    <label>Check Out</label>
                                    <input placeholder='Check Out Time' name='checkOut' value={this.state.checkOut} 
                                        onChange={this.changeCheckOutHandler} className='form-control' type='time' />
                                </div>
                                <br></br>
                                <div className='form-group'>
                                    <label>Lunch Break Start</label>
                                    <input placeholder='Lunch Break Start' name='lunchBreakStart' type='time' 
                                    value={this.state.lunchBreakStart} onChange={this.changeLunchBreakStartHandler}
                                    className='form-control' />
                                </div>
                                <br></br>
                                <div className='form-group'>
                                    <label>Lunch Break End</label>
                                    <input placeholder='Lunch Break End' name='lunchBreakEnd' type='time'
                                     value={this.state.lunchBreakEnd} onChange={this.changeLunchBreakEndHandler}
                                    className='form-control' />
                                </div>
                                <button className='btn btn-success' onClick={this.saveEmployee}>Save</button>
                                <Link to={`/${this.state.employeeId}/get-templates`} className='btn btn-danger' style={{marginLeft: "10px"}}>Cancel</Link>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
       </div>
        )
    }
}
