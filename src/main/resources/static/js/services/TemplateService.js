import axios from 'axios';

const  TEMPLATE_API_BASEURL = "http://localhost:8080/employees"

class TemplateService{
    getTemplates(employeeId){
        return axios.get(TEMPLATE_API_BASEURL + '/' + employeeId + "/templates");
    }

    addTemplate(template, employeeId){
        return axios.post(TEMPLATE_API_BASEURL + '/' + employeeId + "/templates", template);
    }

    getTemplateById(employeeId, templateId){
        return axios.get(TEMPLATE_API_BASEURL+'/'+employeeId+'/templates/'+templateId)
    }

    updateTemplate(employeeId, templateId, template){
        return axios.patch(TEMPLATE_API_BASEURL+'/'+employeeId+'/templates/'+templateId, template )
    }
}

export default new TemplateService();