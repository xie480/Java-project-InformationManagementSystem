import request from "@/utils/request";

export const findAllLog = (operateEmpName,begin,end,page,pageSize) => 
    request.get(`/log/page?operateEmpName=${operateEmpName}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)