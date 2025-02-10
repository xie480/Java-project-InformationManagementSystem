import request from "@/utils/request";

export const findAllDept = () =>request.get('/depts');

export const saveDept = (data) =>request.post('/depts', data);

export const findByIdDept = (id) => request.get(`/depts/${id}`);

export const updateDept = (data) => request.put('/depts', data);

export const deleteDept = (id) => request.delete(`/depts?id=${id}`)