import request from "@/utils/request";

export const findAllEmp = (name,gender,begin,end,page,pageSize) => 
    request.get(`/emps?name=${name}&gender=${gender}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)

export const saveEmp = (data) => request.post('/emps', data);

export const findByIdEmp = (id) => request.get(`/emps/${id}`)

export const updateEmps = (data) => request.put('/emps', data);

export const deleteEmp = (ids) => request.delete(`/emps?ids=${ids}`)

export const getId = (name) => request.get(`/emps/getUser?name=${name}`)