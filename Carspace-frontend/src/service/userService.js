
import axiosConfig from "../axiosConfig"

const registerUserCall = (body) => {
    return axiosConfig.post(`auth/signup`, body)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err);
        })
}

const loginUserCall = (body) => {
    return axiosConfig.post(`auth/signin`, body)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err)
        })
}

const checkLoginKey = () => {
    return axiosConfig.get(`auth/checkkey`)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err)
        })
}
const getUserProfile = (username) => {
    return axiosConfig.get(`user/${username}`)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err)
        })
}
function throwError (err){
    if (err.response) {
        if(err.response.data.fieldErrors){
            err.response.data.message = err.response.data.fieldErrors[0].field + ": " + err.response.data.fieldErrors[0].message;
        }
        throw err.response.data.message;
    } else if (err.request) {
        throw err.request;
    } else {
        throw err;
    }
}

export {
    registerUserCall,
    loginUserCall,
    checkLoginKey,
    getUserProfile
}