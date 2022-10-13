
import axiosConfig from "../axiosConfig"

const createAuctionCall = (body) => {
    return axiosConfig.post(`auction`, body)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err)
        })
}
const getAuctionsCall = () => {
    return axiosConfig.get(`auction`)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err)
        })
}
const getAuctionById = (id) => {
    return axiosConfig.get(`auction/` + id)
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
    createAuctionCall,
    getAuctionsCall,
    getAuctionById
}