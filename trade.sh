while :
do
	java -jar cryptotrading_v0.1.3.jar

	if [ -e ./break.txt ]
	then
		break
	else
		echo "=============="
		echo "Program reInit"
		echo "=============="
	fi
done
