package com.kirkbushman.araw.exceptions

class UploadContractFieldMissingException(field: String) :
    Exception("The field '$field' was not found while creating UploadData!")
