
import axiosConfig from "../axiosConfig"

const registerUserCall = (body) => {
    return axiosConfig.post(`auth/signup`, body)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err)
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
function throwError (err){
    if (err.response) {
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
    checkLoginKey
}