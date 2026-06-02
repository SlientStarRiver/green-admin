import service from '../config'

export function getSpeciesList(params) {
    return service({ url: '/plants/species', method: 'get', params })
}

export function createSpecies(data) {
    return service({ url: '/admin/plants/species', method: 'post', data })
}

export function updateSpecies(speciesId, data) {
    return service({ url: `/admin/plants/species/${speciesId}`, method: 'put', data })
}

export function deleteSpecies(speciesId) {
    return service({ url: `/admin/plants/species/${speciesId}`, method: 'delete' })
}
