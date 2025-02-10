import request from "@/utils/request";

export const findAllStudent = (name,degree,clazzId,page,pageSize) => request.get(`/students?name=${name}&degree=${degree}&clazzId=${clazzId}&page=${page}&pageSize=${pageSize}`); 

export const saveStudent = (data) => request.post('/students', data);

export const findByIdStudent = (id) => request.get(`/students/${id}`);

export const updateStudent = (data) => request.put('/students', data);

export const deleteStudent = (ids) => request.delete(`/students/${ids}`);

export const addViolationScore = (id,score) => request.put(`/students/violation/${id}/${score}`);