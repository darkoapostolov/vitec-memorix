package com.momorix.vitecmemorix.extensions

import com.momorix.vitecmemorix.dto.UserDTO
import com.momorix.vitecmemorix.model.User


fun User.mapToDTO() = UserDTO(email, name)