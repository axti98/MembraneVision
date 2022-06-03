cmake_minimum_required(VERSION 3.22)
project(cmd_tool_src)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ../../)

add_executable(tool main.cpp Const.h)

find_package(OpenMP)
if(OpenMP_CXX_FOUND)
    target_link_libraries(tool PUBLIC OpenMP::OpenMP_CXX)
endif()

# Set StartUp Project
set_property( DIRECTORY PROPERTY VS_STARTUP_PROJECT "cmd_tool_src" )

# Find Package
set( OpenCV_DIR "C:\\opencv\\build_mingw" )
option( OpenCV_STATIC OFF )
find_package( OpenCV REQUIRED )

# Set Static Link Runtime Library
if( OpenCV_STATIC )
    foreach( flag_var
            CMAKE_C_FLAGS CMAKE_C_FLAGS_DEBUG CMAKE_C_FLAGS_RELEASE
            CMAKE_C_FLAGS_MINSIZEREL CMAKE_C_FLAGS_RELWITHDEBINFO
            CMAKE_CXX_FLAGS CMAKE_CXX_FLAGS_DEBUG CMAKE_CXX_FLAGS_RELEASE
            CMAKE_CXX_FLAGS_MINSIZEREL CMAKE_CXX_FLAGS_RELWITHDEBINFO )
        if( ${flag_var} MATCHES "/MD" )
            string( REGEX REPLACE "/MD" "/MT" ${flag_var} "${${flag_var}}" )
        endif()
    endforeach()
endif()

if( OpenCV_FOUND )
    # Additional Include Directories
    include_directories( ${OpenCV_INCLUDE_DIRS} )

    # Additional Library Directories
    link_directories( ${OpenCV_LIB_DIR} )

    # Additional Dependencies
    target_link_libraries( tool PUBLIC ${OpenCV_LIBS} )
endif()