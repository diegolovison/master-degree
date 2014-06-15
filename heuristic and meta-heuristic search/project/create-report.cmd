call build.cmd

call run.cmd 20 5 > report\20-5.log
echo "20 5"
call run.cmd 20 10 > report\20-10.log
echo "20 10"
call run.cmd 20 20 > report\20-20.log
echo "20 20"

call run.cmd 50 5 > report\50-5.log
echo "50 5"
call run.cmd 50 10 > report\50-10.log
echo "50 10"
call run.cmd 50 20 > report\50-20.log
echo "50 20"

call run.cmd 100 5 > report\100-5.log
echo "100 5"
call run.cmd 100 10 > report\100-10.log
echo "100 10"
call run.cmd 100 20 > report\100-20.log
echo "100 20"

call run.cmd 200 10 > report\200-10.log
echo "200 10"
call run.cmd 200 20 > report\200-20.log
echo "200 20"