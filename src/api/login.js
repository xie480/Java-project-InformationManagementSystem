import request from "@/utils/request";

export const loginSystem = (data) => request.post('/login', data);