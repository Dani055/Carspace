
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
const editAuctionCall = (body, id) => {
    return axiosConfig.put(`auction/` + id, body)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err)
        })
}
const deleteAuctionCall = (id) => {
    return axiosConfig.delete(`auction/` + id)
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
    getAuctionById,
    editAuctionCall,
    deleteAuctionCall
}