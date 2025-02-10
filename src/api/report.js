import request from "@/utils/request";

export const reportEmpGender = () => request.get('/report/empGenderData');

export const reportEmpJob = () => request.get('/report/empJobData');

export const reportStuDegree = () => request.get('/report/studentDegreeData');

export const reportStuClazz = () => request.get('/report/studentCountData');