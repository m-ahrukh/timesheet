import React, { Component } from 'react'
import TemplateService from '../services/TemplateService'
import { useParams } from 'react-router-dom';
import EmployeeService from '../services/EmployeeService';
export function withRouter(Children) {
    return (props) => {
        const match = { params: useParams() };
        return <Children {...props} match={match} />
    }
}

class GetTemplate extends Component {
    constructor(props) {
        super(props)

        this.state = {
            templates: [],
            employeeId: this.props.match.params.id,

        }

        this.addTemplate = this.addTemplate.bind(this)
        this.editTemplate = this.editTemplate.bind(this)
        this.handleEvent = this.handleEvent.bind(this)
    }

    addTemplate(){
        window.location.replace(`/${this.state.employeeId}/add-template`);
        // this.props.history.push('/add-employee');
    }

    editTemplate(id){
        console.log(this.state.employeeId + "  " + this.state.id)
        window.location.replace(`/${this.state.employeeId}/update-template/${id}`);
        //this.props.history.push(`/update-employee/${id}`)
    }

    componentDidMount() {
        console.log("id "+ this.state.employeeId);
        TemplateService.getTemplates(this.state.employeeId).then((res) =>{
            this.setState({templates: res.data})
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
                <h2 className='text-center'>Timesheet Templates</h2>
                <button className='btn btn-primary' onClick={this.addTemplate}>Add Template</button>
                <div className='row'>
                    <table className='table table-striped table-bordered'>
                        <thead>
                            <tr>
                                <th>Template ID</th>
                                <th>Template Day</th>
                                <th>Created On</th>
                                <th>Check In</th>
                                <th>Check Out</th>
                                <th>Lunch Break Start</th>
                                <th>Lunch Break End</th>
                                <th>Actions</th>
                            </tr>
                        </thead>

                        <tbody>
                            {
                                this.state.templates.map(
                                    template => 
                                    <tr key = {template.id}>
                                        <td>{template.id}</td>
                                        <td>{template.templateDay}</td>
                                        <td>{template.createdOn}</td>
                                        <td>{template.checkIn}</td>
                                        <td>{template.checkOut}</td>
                                        <td>{template.lunchBreakStart}</td>
                                        <td>{template.lunchBreakEnd}</td>
                                        <td>
                                        <button onClick={ () => {this.editTemplate(template.id)}} className='btn btn-info' >Update</button>
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


export default withRouter(GetTemplate);