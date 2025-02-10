import request from "@/utils/request";

export const findAllClazz = (name,beginDate,endDate,page,pageSize) => request.get(`/clazzs?name=${name}&beginDate=${beginDate}&endDate=${endDate}&page=${page}&pageSize=${pageSize}`)

export const saveClazz = (data) => request.post('/clazzs', data)

export const findByIdClazz = (id) => request.get(`/clazzs/${id}`)

export const updateClazzs = (data) => request.put('/clazzs', data);

export const deleteClazz = (id) => request.delete(`/clazzs/${id}`)