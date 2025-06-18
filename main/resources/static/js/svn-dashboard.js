// // Chart Initialization
// function initCommitChart(data) {
//     new Chart(document.getElementById('commitChart'), {
//         type: 'bar',
//         data: {
//             labels: data.users,
//             datasets: [{
//                 label: 'Commits',
//                 data: data.counts
//             }]
//         }
//     });
// }


function initCommitChart(chartData) {
    const ctx = document.getElementById('commitChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: chartData.labels,
            datasets: [{
                label: 'Total Commits',
                data: chartData.data,
                backgroundColor: 'rgba(54, 162, 235, 0.5)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Number of Commits'
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Users'
                    }
                }
            }
        }
    });
}






// Directory Tree
function loadDirectoryTree(repoUrl, path = '') {
    fetch(`/repo/${repoUrl}/tree?path=${encodeURIComponent(path)}`)
        .then(response => response.json())
        .then(data => renderTree(data));
}

function renderTree(items) {
    const treeContainer = document.getElementById('directory-tree');
    treeContainer.innerHTML = items.map(item => `
        <div class="tree-item ${item.isDirectory ? 'folder' : 'file'}" 
             data-path="${item.path}"
             ondblclick="${item.isDirectory ? 'navigateToPath(this)' : 'showFileDiff(this)'}">
            ${item.name}
        </div>
    `).join('');
}





    function openModal() {
    fetch('/svn-dashboard/modal')
        .then(response => response.text())
        .then(html => {
            document.getElementById('modalContent').innerHTML = html;
            document.getElementById('repoModal').style.display = 'block';


            // setTimeout(() => {
            //     const firstInput = document.querySelector('#repoModal input');
            //     if (firstInput) firstInput.focus();
            // }, 50);

            document.getElementById('repoForm').onsubmit = function(e) {
                e.preventDefault();

                const formData = new FormData(this);
                const data = Object.fromEntries(formData.entries());
                fetch('/svn-dashboard/addrepo', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                }).then(resp => {
                    if (resp.ok) {
                        alert('Repository saved!');
                        closeModal();
                        location.reload();
                    } else {
                        alert('Error saving repository');
                    }
                });
            };
        });
}

    function closeModal() {
    document.getElementById('repoModal').style.display = 'none';

}
function deleteRepository(element) {
    const name = element.getAttribute("data-repo-name");
    if (confirm("Are you sure you want to delete?")) {
        fetch('/svn-dashboard/deleteRepo/' + encodeURIComponent(name), {
            method: 'GET'
        }).then(resp => {
            if (resp.ok) {
                alert('Repository deleted successfully!');
                location.reload();
            } else {
                alert('Something went wrong!');
            }
        });
    }
}

