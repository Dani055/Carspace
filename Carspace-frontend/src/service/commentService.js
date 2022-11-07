
import axiosConfig from "../axiosConfig"

const createCommentCall = (body, auctionId) => {
    return axiosConfig.post(`comment/`+auctionId, body)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err)
        })
}

const deleteCommentCall = (commentId) => {
    return axiosConfig.delete(`comment/`+ commentId)
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
    createCommentCall,
    deleteCommentCall
}