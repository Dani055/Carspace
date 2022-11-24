
import axiosConfig from "../axiosConfig"

const createAuctionCall = (body) => {
    return axiosConfig.post(`auction`, body)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err)
        })
}
const getAuctionsByCreatorCall = (creatorId) => {
    return axiosConfig.get(`auction?creatorId=${creatorId}`)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err)
        })
}
const getLiveAuctionsWithFiltersAndPage = (filters, page) => {
    return axiosConfig.get(`auction/filter?location=${filters?.location}&carModel=${filters?.carModel}&minPrice=${filters?.minPrice}&maxPrice=${filters?.maxPrice}&minMileage=${filters?.minMileage}&hasEnded=false&carBrand=${filters?.carBrand}&minYear=${filters?.minYear}&maxYear=${filters?.maxYear}&maxMileage=${filters?.maxMileage}&page=${page}`)
        .then((response) => response.data)
        .catch((err) => {
            throwError(err)
        })
}
const getEndedAuctionsWithFiltersAndPage = (filters, page) => {
    return axiosConfig.get(`auction/filter?location=${filters?.location}&carModel=${filters?.carModel}&minPrice=${filters?.minPrice}&maxPrice=${filters?.maxPrice}&minMileage=${filters?.minMileage}&hasEnded=true&carBrand=${filters?.carBrand}&minYear=${filters?.minYear}&maxYear=${filters?.maxYear}&maxMileage=${filters?.maxMileage}&page=${page}`)
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
    getAuctionsByCreatorCall,
    getLiveAuctionsWithFiltersAndPage,
    getEndedAuctionsWithFiltersAndPage,
    getAuctionById,
    editAuctionCall,
    deleteAuctionCall
}