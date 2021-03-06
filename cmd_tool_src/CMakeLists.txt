cmake_minimum_required(VERSION 3.22)
project(cmd_tool_src)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY C:\\Users\\User\\IdeaProjects\\MembraneVision\\out\\artifacts\\MembraneVision)

find_package(OpenMP)

# Where to find CMake modules and OpenCV
set(OpenCV_DIR "C:\\opencv\\build_mingw\\install")
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake/")

find_package(OpenCV REQUIRED)
include_directories(${OpenCV_INCLUDE_DIRS})

# add libs you need
set(OpenCV_LIBS opencv_core opencv_imgproc opencv_highgui opencv_imgcodecs)

add_executable(tool main.cpp
        Const.h
        SegmentationHandler.cpp SegmentationHandler.h
        alglib/optimization.cpp alglib/optimization.h
        alglib/solvers.cpp alglib/solvers.h
        alglib/stdafx.h
        alglib/linalg.cpp alglib/linalg.h
        alglib/ap.cpp alglib/ap.h
        alglib/alglibmisc.cpp alglib/alglibmisc.h
        alglib/alglibinternal.cpp alglib/alglibinternal.h
        )

# linking
target_link_libraries(tool ${OpenCV_LIBS})
if(OpenMP_CXX_FOUND)
    target_link_libraries(tool OpenMP::OpenMP_CXX)
endif()